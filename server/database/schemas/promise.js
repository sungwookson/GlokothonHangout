let mongoose = require('mongoose');
let schema = mongoose.Schema({
    category : { type : String, required : true },
    uid : { type : String, required : true },
    startDate : { type : String, required : true },
    endDate : { type : String, required : true },
    location : { type : JSON, required : true },
    plan : { type : String, required : true },
    createDate : { type : String, required : true, default : new Date().toLocaleString() }
}, {
    collection: "promise"
});

module.exports = schema;