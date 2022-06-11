import Sequelize from 'sequelize';
import { accesses,requestStatus, problemStatus} from './strings.mjs'

const sequelize = new Sequelize({
    dialect: 'sqlite',
    storage: './licenta.db'
});

const Departament = sequelize.define('department',{
    id: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true
    },
    title:{
        type:Sequelize.STRING,
        allowNull:false,
        unique:true
    }
}, {
    timestamps: false
  })

const Employee  = sequelize.define('employee', {
    id: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true
    },
    firstName:
    {
        type:Sequelize.STRING,
        validate:{
            min:3
        }
    },
    lastName:{
        type:Sequelize.STRING,
        validate:{
            min:3
        }
    },
    password:{
        type:Sequelize.STRING,
        allowNull:false,
        validate:{
            min:3
        }
    },
    email:{
        type:Sequelize.STRING,
        validate:{
            isEmail:true
        }
    },
    experience:{
        type:Sequelize.INTEGER,
        defaultValue: 0
    },
    level:{
        type:Sequelize.INTEGER,
        defaultValue: 1
    },
    isManager:{
        type:Sequelize.BOOLEAN,
    },
    departmentId:{
        type:Sequelize.STRING
    }
},{
    timestamps: false
  });

const Issue = sequelize.define('issue',{
    id: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true
    },
    reason:{
        type:Sequelize.STRING,
        validate:{
            min:5
        }
    },
    priorityLevel:{
        type:Sequelize.STRING,
        allowNull: false
    },
    createDate:{
        type:Sequelize.STRING,
        allowNull: false
    },
    status:{
        type:Sequelize.STRING,
        validate:{
            isIn:[problemStatus]
        }
    }
},{
    timestamps: false
  })

const TemporaryCode  = sequelize.define('temporaryCode', {

    email:{
        type:Sequelize.STRING,
        primaryKey: true,
        validate:{
            isEmail:true
        }
    },
    code:{
        type:Sequelize.INTEGER,
    }

}, {
    timestamps: false
  });

const Log  = sequelize.define('log', {
    id: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true
    },
    action:{
        type:Sequelize.STRING,
        allowNull:false
    },
    createDate:{
        type:Sequelize.STRING,
        allowNull:false
    },
    employeeId:{
        type:Sequelize.UUID,
        allowNull:false
    }
}, {
    timestamps: false
  });

const Request = sequelize.define('request',{
    id: {
        type: Sequelize.UUID,
        defaultValue: Sequelize.UUIDV4,
        primaryKey: true
    },
    reason:{
        type:Sequelize.STRING,
        validate:{
            min:5
        }
    },
    createDate:{
        type:Sequelize.STRING,
        allowNull: false
        
    },
    requestDate:{
        type:Sequelize.STRING,
        allowNull: false
        
    },
    status:{
        type:Sequelize.STRING,
        validate:{
            isIn:[requestStatus]
        }
    },
    type:{
        type:Sequelize.INTEGER,
        allowNull:false
    },
    numberOfHours:{
        type:Sequelize.INTEGER,
        allowNull:false
    },
    employeeId:{
        type: Sequelize.UUID,
}
}, {
timestamps: false
})

const Access = sequelize.define('access',{
id: {
    type: Sequelize.UUID,
    defaultValue: Sequelize.UUIDV4,
    allowNull: false,
    primaryKey: true
},
type:{
    type:Sequelize.STRING,
    validate:{
        isIn:[accesses]
    }
},
givenTo:{
    type:Sequelize.UUID,
    allowNull:false
    
},
from:{
    type:Sequelize.UUID,
    allowNull:false
},
startDate:{
    type:Sequelize.STRING,
    allowNull:false
},
endDate:{
    type:Sequelize.STRING,
    allowNull:false
}
}, {
timestamps: false
})


const Prize = sequelize.define('prize',{
id: {
    type: Sequelize.UUID,
    defaultValue: Sequelize.UUIDV4,
    allowNull: false,
    primaryKey: true
},
name:{
    type:Sequelize.STRING,
    allowNull:false
},
description:{
    type:Sequelize.STRING,
},
necessaryLevel:{
    type:Sequelize.INTEGER,
    defaultValue:0
}
}, {
timestamps: false
})

const Item = sequelize.define('item',{
id: {
    type: Sequelize.UUID,
    defaultValue: Sequelize.UUIDV4,
    allowNull: false,
    primaryKey: true
},
prizeId:{
    type:Sequelize.UUID,
},
employeeId:{
    type:Sequelize.UUID
}

}, {
timestamps: false
})

Employee.hasMany(Request, {
    foreignKey: 'employeeId'
});

Request.belongsTo(Employee, {
    foreignKey: 'employeeId'
});

Departament.hasMany(Employee, {
    foreignKey:'departmentId'
})

Employee.belongsTo(Departament, {
    foreignKey:"departmentId"
})

Employee.hasMany(Log,{
    foreignKey: 'employeeId'
});

Log.belongsTo(Employee,{
    foreignKey: 'employeeId'
})

Employee.hasMany(Item,{
    foreignKey:'employeeId'
})

Item.belongsTo(Employee,{
    foreignKey:'employeeId'
})

Prize.hasMany(Item,{
    foreignKey:'prizeId'
})

Item.belongsTo(Prize,{
    foreignKey:'prizeId'
})

async function initialize() {
    await sequelize.authenticate();
    await sequelize.sync({
        //force: true
    });
}

export {
    initialize,
    Employee,
    Departament,
    Request,
    Access,
    Issue,
    TemporaryCode,
    Log,
    Prize,
    Item
}