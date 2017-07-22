let express = require('express');
let session = require('express-session');
const fileUpload = require('express-fileupload');
var bodyParser = require('body-parser');
let morgan = require('morgan');
let router = require('./routes');
let database = require('./database');
let app = express();

app.set('port', 8080);

app.use(morgan('dev'));
app.use(fileUpload());
app.use(bodyParser.urlencoded({
    extended: false
}));

app.use(session({
    key: 'entrykey',
    secret: 'secret',
    resave: false
}));



app.use(bodyParser.json());
// app.post('/upload',function(req,res){
//     console.log(req.files);
//     let sampleFile = req.files.sampleFile;
//     sampleFile.mv('./filename.jpg', function (err) {
//         if (err)
//       return res.status(500).send(err);
 
//     res.send('File uploaded!');
// });

// });
app.use('/', router);
app.listen(app.get('port'), function () {
    console.log("Started :: " + app.get('port'));
    database.connect(app);
});