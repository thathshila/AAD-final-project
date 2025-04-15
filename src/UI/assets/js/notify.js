// payment-notification-server.js
const express = require('express');
const crypto = require('crypto');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();

// Middleware
app.use(bodyParser.json());
app.use(cors()); // Enable CORS for all routes

// PayHere merchant credentials
const MERCHANT_ID = '1229893';
const MERCHANT_SECRET = 'MjI1NTUwNjA4OTQwODMxMjQ2MTIxxOD';

// Payment notification endpoint
app.post('/api/v1/payment/notify', (req, res) => {
    try {
        console.log('Payment notification received:', req.body);

        // Verify the authenticity of the request
        const { merchant_id, order_id, payment_id, payhere_amount, payhere_currency, status_code, md5sig } = req.body;

        // Validate merchant ID
        if (merchant_id !== MERCHANT_ID) {
            console.error('Invalid merchant ID');
            return res.status(401).json({ error: 'Unauthorized' });
        }

        // Verify MD5 signature if provided
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

        // Process the payment based on status_code
        // 2 = Success, 0 = Pending, -1 = Canceled, -2 = Failed, -3 = Chargedback
        if (status_code === 2) {
            // Payment successful - update your database
            console.log('Payment successful for order:', order_id);
            // Update order status in your database
            // Example: await updateOrderStatus(order_id, 'paid');
        } else if (status_code === 0) {
            console.log('Payment pending for order:', order_id);
            // Handle pending payment
        } else {
            console.log(`Payment failed for order: ${order_id}, status: ${status_code}`);
            // Handle failed payment
        }

        // Always return success to PayHere to acknowledge receipt
        return res.status(200).json({ status: 'success' });

    } catch (error) {
        console.error('Error processing payment notification:', error);
        return res.status(500).json({ error: 'Internal server error' });
    }
});

// Start the server
const PORT = process.env.PORT || 8080;
app.listen(PORT, () => {
    console.log(`Payment notification server running on port ${PORT}`);
});

// Utility function for MD5 hash
function md5(string) {
    return crypto.createHash('md5').update(string).digest('hex');
}