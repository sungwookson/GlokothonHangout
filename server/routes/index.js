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

router.route('/users/info').post(function(req, res){
    

});
router.route('/users/info').put(function(req, res){

});
router.route('/users/info/:userId').get(function(req, res){    

});


module.exports = router;