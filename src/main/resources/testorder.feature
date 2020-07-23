Feature: Ser capaz de hacer un pedido

  Scenario: Ingresar al Portal
    Given Ir a la Tienda de DXC
    When Ingreso su usuario "admin" en el campo login y password
    Then El deberia poder ingresar a la pagina principal

  Scenario: Agregar pedido al carrito
    Given Ir al catalogo virtual
    When Buscar "fish" y Elegir un "Goldfish"
    Then El deberia poder agregar el pedido al carrito

  Scenario: Proceder con el pago y enviar pedido
    Given Ir al carrito de compras
    When Ingreses tus datos de pago de tarjeta con "9999999999"
    Then Realiza pago y envia pedido

  Scenario: Valida su pedido
    Given Ir a revisar su cuenta
    When Ingresa su pedido obtenido desde su cuenta
    Then Valida y cierra sesion