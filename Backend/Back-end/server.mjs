import express, { json } from 'express';
import { initialize } from './repository.mjs';
import cors from 'cors';
import routes from './routes.mjs';

const application = express();
application.use(json());
application.use(cors());

application.use('/api', routes);

const port = 8080;

application.listen(port, async () =>{
    try {

        await initialize();
        
    } catch (error) {
        console.error(error);
    }
});
