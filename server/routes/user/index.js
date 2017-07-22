var router = require("express").Router();



/**
 * ::::::::: USER :::::::::
 * 
 *      UID, 
 *      email,
 *      nickname, 
 *      picture,
 *      detail
 */

router.route('/info').post(function(req, res){
    

});
router.route('/info').put(function(req, res){

});
router.route('/info/:userId').get(function(req, res){    

});


module.exports = router;