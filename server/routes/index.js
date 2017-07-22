var router = require("express").Router();
let userRouter = require("./user");
let promiseRouter = require("./promise");

router.route('/profiles/:picURL').get(function (req, res) {
  var file = './profiles/'+ req.params.picURL;
  res.download(file); 
});
router.use('/user', userRouter);
router.use('/promise', promiseRouter);

module.exports = router;