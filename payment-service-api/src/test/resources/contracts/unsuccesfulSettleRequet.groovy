package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description('''
        Ensures that the payment service does not settle payments for invalid requests
    ''')
    request {
        method 'POST'
        url '/settle'
        body([
                orderId: 100L,
                price: -2.0
        ])
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body('0')
        headers {
            contentType(textPlain())
        }
    }
}
