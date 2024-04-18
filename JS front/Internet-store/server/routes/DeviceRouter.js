const Router = require('express')
const router = Router()
const controller = require('../controllers/deviceController')
const checkRoleMiddleware = require('../middleware/checkRoleMiddleware')

router.post('/',checkRoleMiddleware('ADMIN'),controller.create)
router.get('/',controller.getAll)
router.get('/:id',controller.getById)
module.exports = router

