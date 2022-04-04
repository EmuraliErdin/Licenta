import nodemailer from 'nodemailer'

var transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
      user: 'rocodoco400@gmail.com',
      pass: 'Iphonelover123'
    }
  });


function sendEmailTo(text, emailReciever){
    var mailOptions = {
        from: 'youremail@gmail.com',
        to: emailReciever,
        subject: 'Account verification',
        text: text
      }

      transporter.sendMail(mailOptions, function(error, info){
        if (error) {
          console.log(error);
        } else {
          console.log('Email sent: ' + info.response);
        }
      });
}

export{
    sendEmailTo
}