let schema = require('../schemas/user');
let mongoose = require('mongoose');

schema.static('getInfoFromUid', function(uid, resolve){
    
    this.findOne({"uid" : uid}, function(err, doc){
        
        resolve(doc);
    });
})

let model = mongoose.model("user", schema);

module.exports = model;