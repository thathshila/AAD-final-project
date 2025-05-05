
const express = require('express');
const crypto = require('crypto');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();

app.use(bodyParser.json());
app.use(cors());
const MERCHANT_ID = '1229893';
const MERCHANT_SECRET = 'MjI1NTUwNjA4OTQwODMxMjQ2MTIxxOD';

app.post('/api/v1/payment/notify', (req, res) => {
    try {
        console.log('Payment notification received:', req.body);


        const { merchant_id, order_id, payment_id, payhere_amount, payhere_currency, status_code, md5sig } = req.body;

        if (merchant_id !== MERCHANT_ID) {
            console.error('Invalid merchant ID');
            return res.status(401).json({ error: 'Unauthorized' });
        }

        if (md5sig) {
            const localMd5Sig = crypto.createHash('md5')
                .update(merchant_id + order_id + payhere_amount + payhere_currency + status_code +
                    md5(MERCHANT_SECRET).toUpperCase())
                .digest('hex')
                .toUpperCase();

            if (localMd5Sig !== md5sig) {
                console.error('Invalid signature');
                return res.status(401).json({ error: 'Invalid signature' });
            }
        }


        if (status_code === 2) {
            console.log('Payment successful for order:', order_id);
        } else if (status_code === 0) {
            console.log('Payment pending for order:', order_id);
        } else {
            console.log(`Payment failed for order: ${order_id}, status: ${status_code}`);
        }

        return res.status(200).json({ status: 'success' });

    } catch (error) {
        console.error('Error processing payment notification:', error);
        return res.status(500).json({ error: 'Internal server error' });
    }
});

const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
    console.log(`Payment notification server running on port ${PORT}`);
});


function md5(string) {
    return crypto.createHash('md5').update(string).digest('hex');
}