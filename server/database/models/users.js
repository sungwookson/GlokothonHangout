let schema = require('../schemas/users');
let mongoose = require('mongoose');

schema.static('method', function(parameter){
    console.log(parameter);
})

let model = mongoose.model("users", schema);

module.exports = model;