let mongoose = require('mongoose');
let schema = mongoose.Schema({
    "index": { type: String }

}, {
    collection: "users"
});

module.exports = schema;