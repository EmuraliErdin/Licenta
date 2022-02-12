import Sequelize from 'sequelize';

const sequelize = new Sequelize({
    dialect: 'sqlite',
    storage: './test.db'
});

const Meeting  = sequelize.define('meeting', {
    id: {
        type: Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey: true
    },
    description:
    {
        type:Sequelize.STRING,
        validate:{
            min:3
        }
    },
    url:{
        type:Sequelize.STRING,
        validate:{
            isUrl:true
        }
    },
    date:{
        type:Sequelize.DATE,
    }   


});

const Participant = sequelize.define('participant',{
    id: {
        type: Sequelize.INTEGER,
        autoIncrement:true,
        primaryKey: true
    },
    name:{
        type:Sequelize.STRING,
        validate:{
            min:5
        }
    },

})


Meeting.hasMany(Participant, {
    foreignKey: 'meetingId'
});

Participant.belongsTo(Meeting, {
    foreignKey: 'meetingId'
});


async function initialize() {
    
    
    await sequelize.authenticate();
    await sequelize.sync({
        force:true,
        alter: true
    });
}

export {
    initialize,
    Meeting,
    Participant
}