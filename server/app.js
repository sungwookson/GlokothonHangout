let express = require('express');
let session = require('express-session');
var bodyParser = require('body-parser');
let morgan = require('morgan');
var redis = require("redis"),
    client = redis.createClient();


let app = express();

app.set('port', 8080);

app.use(morgan('dev'));
app.use(bodyParser.urlencoded({
    extended: false
}));

app.use(session({
    key: 'entrykey',
    secret: 'secret',
    resave: false
}));



app.use(bodyParser.json());

app.listen(app.get('port'), function () {
    console.log("Started :: " + app.get('port'));
});