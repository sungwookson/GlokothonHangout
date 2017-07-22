let mongoose = require('mongoose');
let schema = mongoose.Schema({
    "index": { type: String }

}, {
    collection: "promise"
});

let model = mongoose.model("promise", schema);

module.exports = model;