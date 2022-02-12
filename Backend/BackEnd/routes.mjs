import express, { request, response } from 'express';
import { Meeting, Participant } from './repository.mjs';
import {
    getRecords, postRecord, deleteRecords,
    getRecord, headRecord, deleteRecord, putRecord, patchRecord, 
    getChildrenOfParent, postChildOfParent,
    getChildOfParent, deleteChildOfParent, putChildOfParent
} from './service.mjs';

const router = express.Router();


router.route('/meetings')
    .get((request, response)=> getRecords(Meeting, request, response))
    .post((request, response)=> postRecord(Meeting, request, response))
    .delete((request, response)=> deleteRecords(Meeting, request, response))
    
 router.route('/meetings/:id')
    .get((request, response)=> getRecord(Meeting, request, response))
    .head((request, response)=> headRecord(Meeting, request, response))//verifica daca exista
    .put((request, response)=> putRecord(Meeting, request, response))
    .patch((request, response)=> patchRecord(Meeting, request, response))
    .delete((request, response)=> deleteRecord(Meeting, request, response))

router.route('/participants')
    .get((request, response)=> getRecords(Participant, request, response))
    .post((request, response)=> postRecord(Participant, request, response))
    .delete((request, response)=> deleteRecords(Participant, request, response))
    
 router.route('/participants/:id')
    .get((request, response)=> getRecord(Participant, request, response))
    .head((request, response)=> headRecord(Participant, request, response))//verifica daca exista
    .put((request, response)=> putRecord(Participant, request, response))
    .patch((request, response)=> patchRecord(Participant, request, response))
    .delete((request, response)=> deleteRecord(Participant, request, response))
 
   router.route('/meetings/:fid/participants')
   .get((request,response)=>getChildrenOfParent(Meeting,'participant',request,response))
   .post((request,response)=>postChildOfParent(Meeting,'meeting',Participant,request,response))

   router.route('/meetings/:fid/participants/:sid')
   .delete((request,response)=>deleteChildOfParent(Meeting,'participant',request,response))
   .put((request,response)=>putChildOfParent(Meeting,'participant',request,response))


    export default router;