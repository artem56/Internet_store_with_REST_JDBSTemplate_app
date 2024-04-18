const express = require('express')
const fileUpload = require('express-fileupload')
const sequelize = require('./db')
require('dotenv').config()
const models = require('./models/models')
const cors = require('cors')
const PORT = process.env.PORT || 5000
const app = express()
const router = require('./routes/index')
const ErrorHandler = require('./middleware/ErrorHandlingMiddleware')
const path = require('path')

app.use(cors())
app.use(fileUpload({}))
app.use(express.json())
app.use(express.static(path.resolve(__dirname, 'static')))
app.use('/api', router)
app.use(ErrorHandler)

const start = async () => {
    try {

        await sequelize.authenticate()
        await sequelize.sync()
        app.listen(PORT, () => console.log(`Server start on port ${PORT}`))

    } catch (e) {
        console.log(e)
    }
}

start()