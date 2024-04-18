const Router = require('express')

const router = Router()

const brandRouter = require('./BrandRouter')
const deviceRouter = require('./DeviceRouter')
const typeRouter = require('./TypeRouter')
const userRouter = require('./UserRouter')

router.use('/user',userRouter)
router.use('/type',typeRouter)
router.use('/brand',brandRouter)
router.use('/device',deviceRouter)

module.exports = router

