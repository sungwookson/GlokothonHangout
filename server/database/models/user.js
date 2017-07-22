let schema = require('../schemas/user');
let mongoose = require('mongoose');

schema.static('getAgeFromUid', function(uid, resolve){
    console.log(uid);
    this.findOne({"uid" : uid}, function(err, doc){
        console.log(doc.age);
        resolve(doc.age);
    });
})

let model = mongoose.model("user", schema);

module.exports = model;