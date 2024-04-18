const bcrypt = require('bcrypt')
const jsonToken = require('jsonwebtoken')
require('dotenv').config()
const { User, Basket } = require('../models/models')
const ApiError = require('../Error/ApiError')

const generateJwt = (user) => {
    return jsonToken.sign(
        { id: user.id, email:user.email, role:user.role },
        process.env.SECRET_KEY,
        { expiresIn: '24h' })
}

class UserController {


    async registration(req, res, next) {
        const { email, password, role } = req.body
        if (!email || !password) {
            return next(ApiError.badRequest('Incorrect email or password'))
        }
        const candidate = await User.findOne({ where: { email } })
        if (candidate) {
            return next(ApiError.badRequest('This email is already used'))
        }
        const hashPassword = await bcrypt.hash(password, 5)
        const user = await User.create({ email, password: hashPassword, role })
        const basket = await Basket.create({ user_id: user.id })
        const jwt = generateJwt(user)
        return res.json({ jwt })
    }

    async login(req, res, next) {
        const { email, password } = req.body
        if (!email || !password) {
            return next(ApiError.badRequest('Incorrect email или пароль'))
        }
        const user = await User.findOne({ where: { email } })
        if (!user) {
            return next(ApiError.badRequest('Incorrect email or password'))
        }
        const comparePassw = bcrypt.compareSync(password, user.password)
        if (!comparePassw) {
            return next(ApiError.badRequest('Incorrect email or password'))
        }
        const jwt = generateJwt(user)
        return res.json({ jwt })
    }

    async check(req, res, next) {
        const jwt = generateJwt(req.user)
        return res.json({jwt})
    }
}
module.exports = new UserController()

