let schema = require('../schemas/user');
let mongoose = require('mongoose');

schema.static('getAgeFromUid', function(uid){
    this.findOne({"uid" : uid}, function(err, doc){
        return doc.age;
    });
})

let model = mongoose.model("user", schema);

module.exports = model;