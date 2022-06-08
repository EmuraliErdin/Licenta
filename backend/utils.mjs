import nodemailer from 'nodemailer'
import {google} from 'googleapis'

const CLIENT_ID = '188664501659-6198fbni2ccbiih2bthtik8jnd0ghbjj.apps.googleusercontent.com'
const CLIENT_SECRET = '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'
const REDIRECT_URI = 'https://developers.google.com/oauthplayground'
const REFRESH_TOKEN ='1//04JflYVCrZXDGCgYIARAAGAQSNwF-L9IrG0efSmLqd3je6dVgCW6OWQRZ3SxWJkvehx7WRiQ0uPDNnFUQP2-x79JPQ23aPm9Ixfs'

const oAuth2Client = new google.auth.OAuth2(CLIENT_ID, CLIENT_SECRET, REDIRECT_URI);
oAuth2Client.setCredentials({refresh_token:REFRESH_TOKEN})

async function sendEmailTo(text, emailReciever, subject){
  try{
    const accessToken = await oAuth2Client.getAccessToken()
    const transporter = nodemailer.createTransport({
          service: 'gmail',
          auth: {
            type:'OAuth2',
            clientId: CLIENT_ID,
            clientSecret:CLIENT_SECRET,
            refreshToken: REFRESH_TOKEN,
            accessToken: accessToken,
            user: 'rocodoco400@gmail.com',
            pass: 'Iphonelover123'
            
          }
        })
        const mailOptions ={
          from : 'Erdin <rocodoco400@gmail.com>',
          to : emailReciever,
          subject : subject,
          text: text
        }

        const result = transporter.sendMail(mailOptions,  function(error, info){
                  if (error) {
                    console.log(error);
                  } else {
                    console.log('Email sent: ' + info.response)
                  }
                }
                )

        return result;
  }catch(err){
    console.log(err);;
  }
}


function sortByDate(arr) {
  const sorter = (a, b) => {

    if(a.status==='PENDING' || !b.status==='PENDING'){
      return 1;
    }

     return new Date(a.requestDate).getTime() - new Date(b.requestDate).getTime();
  }
  return arr.sort(sorter)
};

const padTo2Digits = (num) => {
  return num.toString().padStart(2, '0');
}

const formatDate = (date) => {
  return [
    date.getFullYear(),
    padTo2Digits(date.getMonth() + 1),
    padTo2Digits(date.getDate()),
  ].join('-');
}

export{
    sendEmailTo, sortByDate, formatDate
}