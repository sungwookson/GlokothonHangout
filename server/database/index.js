let mongoose = require('mongoose');
let config = require('./config');

let database = {};

exports.connect = function (app) {
    mongoose.Promise = global.Promise;
    mongoose.connect(config.dbURL);
    database.connection = mongoose.connection;
    database.connection.on('error', console.error.bind(console, 'mongoose connection error.'));
    database.connection.on('open', function () {
        console.log('데이터베이스에 연결되었습니다. : ' + config.dbURL);
        init(app);
    });
    database.connection.on('disconnected', this.connect);
}


function init(app) {
    for (let i = 0 ; i < config.models.length; i++) {
        database[config.models[i].name] = require(config.models[i].src);
    }
    app.set('database', database);
}