let mongoose = require('mongoose');
let schema = mongoose.Schema({
    "index": { type: String }

}, {
    collection: "promise"
});

module.exports = schema;