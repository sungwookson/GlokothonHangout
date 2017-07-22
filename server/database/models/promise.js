let schema = require('../schemas/promise');
let mongoose = require('mongoose');

schema.static('method', function(parameter){
    console.log(parameter);
})

let model = mongoose.model("promise", schema);

module.exports = model;