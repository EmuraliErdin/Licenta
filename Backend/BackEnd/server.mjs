import express, { json } from 'express';
import { initialize } from './repository.mjs';
import cors from 'cors';
import routes from './routes.mjs';

const application = express();
application.use(json());
application.use(cors());

application.use('/api', routes);

application.listen(8080, async () =>{
    try {

        await initialize();
        
    } catch (error) {
        console.error(error);
    }
});
