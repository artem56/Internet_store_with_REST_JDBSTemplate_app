const jsonToken = require('jsonwebtoken')
require('dotenv').config()
module.exports = function (req, res, next)
{
	if (req.method === "OPTIONS") {
		next()
	}
	try {
		const jwt = req.headers.authorization.split( ' ')[1]
		if (!jwt) {
			return res.status(401).json({ message: 'user is not authorized' })
		}
		const decoded = jsonToken.verify(jwt, process.env.SECRET_KEY)
		req.user = decoded
		next()

	} catch (e) {
		 res.status(401).json({message:'user is not authorized'})
	}
}