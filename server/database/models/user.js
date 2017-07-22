let schema = require('../schemas/user');
let mongoose = require('mongoose');

schema.static('method', function(parameter){
    console.log(parameter);
})

let model = mongoose.model("user", schema);

module.exports = model;