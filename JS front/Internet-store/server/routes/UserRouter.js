﻿const Router = require('express')
const router = Router()
const userController = require('../controllers/userController')
const authMiddleware = require('../middleware/aurhMiddleware')

router.post('/registration',userController.registration)
router.post('/login',userController.login)
router.get('/auth', authMiddleware, userController.check)

module.exports = router
