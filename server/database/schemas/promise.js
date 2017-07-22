let mongoose = require('mongoose');
let schema = mongoose.Schema({
    uid : { type : String, require : true },
    startDate : { type : Date, require : true },
    endDate : { type : Date, require : true },
    location : { type : JSON, require : true },
    plan : { type : String, require : true }
}, {
    collection: "promise"
});

module.exports = schema;