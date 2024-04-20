package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description('''
        Ensures that the payment service settles payments correctly for valid requests
    ''')
    request {
        method 'POST'
        url '/settle'
        body([
                orderId: 100L,
                price: 50.0
        ])
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body('1')
        headers {
            contentType(textPlain())
        }
    }
}
