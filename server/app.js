let express = require('express');
let session = require('express-session');
var bodyParser = require('body-parser');
let morgan = require('morgan');
let router = require('./routes');
let database = require('./database');
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
app.use('/', router);
app.listen(app.get('port'), function () {
    console.log("Started :: " + app.get('port'));
    database.connect(app);
});