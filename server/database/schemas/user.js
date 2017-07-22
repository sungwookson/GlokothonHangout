let mongoose = require('mongoose');
let schema = mongoose.Schema({
    "index": { type: String }

}, {
    collection: "user"
});

module.exports = schema;