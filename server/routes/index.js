var router = require("express").Router();
let userRouter = require("./user");
let promiseRouter = require("./promise");


router.use('/user', userRouter);
router.use('/promise', promiseRouter);

module.exports = router;