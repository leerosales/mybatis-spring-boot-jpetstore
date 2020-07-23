/*
 *    Copyright 2016-2020 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
import com.codeborne.selenide.Selenide;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class TestOrder {

    protected static String orderId = "";

    @Given("^Ir a la Tienda de DXC$")
    public void ir_a_la_Tienda_de_DXC() throws Exception {
        // Open the home page
        open("/");
        assertThat(title()).isEqualTo("JPetStore Demo");

        // Move to the top page
        $(By.linkText("Enter the Store")).click();
        $(By.id("WelcomeContent")).shouldHave(exactText(""));
    }

    @When("^Ingreso su usuario \"([^\"]*)\" en el campo login y password$")
    public void ingreso_su_usuario_en_el_campo_login_y_password(String arg1) throws Exception {
        Selenide.sleep(3000);
        // Move to sign in page & sign
        $(By.linkText("Sign In")).click();
        $(By.name("username")).val("j2ee");
        $(By.name("password")).val("j2ee");
        $(By.id("login")).click();
    }

    @Then("^El deberia poder ingresar a la pagina principal$")
    public void el_deberia_poder_ingresar_a_la_pagina_principal() throws Exception {
        $(By.id("WelcomeContent")).$(By.tagName("span")).shouldHave(text("ABC"));
    }

    @Given("^Ir al catalogo virtual$")
    public void ir_al_catalogo_virtual() throws Exception {
        Selenide.sleep(3000);
        // Search items
        $(By.name("keywords")).val("fish");
        $(By.id("searchProducts")).click();
        $$(By.cssSelector("#Catalog table tr")).shouldHave(size(3));
    }

    @When("^Buscar \"([^\"]*)\" y Elegir un \"([^\"]*)\"$")
    public void buscar_y_Elegir_un(String arg1, String arg2) throws Exception {
        Selenide.sleep(3000);
        // Select item
        $(By.linkText("Fresh Water fish from China")).click();
        $(By.cssSelector("#Catalog h2")).shouldHave(text("Goldfish"));
    }

    @Then("^El deberia poder agregar el pedido al carrito$")
    public void el_deberia_poder_agregar_el_pedido_al_carrito() throws Exception {
        Selenide.sleep(3000);
        // Add a item to the cart
        $(By.linkText("Add to Cart")).click();
        $(By.cssSelector("#Catalog h2")).shouldHave(text("Shopping Cart"));
    }

    @Given("^Ir al carrito de compras$")
    public void ir_al_carrito_de_compras() throws Exception {
        Selenide.sleep(3000);
        // Checkout cart items
        $(By.linkText("Proceed to Checkout")).click();
        assertThat(title()).isEqualTo("JPetStore Demo");
    }

    @When("^Ingreses tus datos de pago de tarjeta con \"([^\"]*)\"$")
    public void ingreses_tus_datos_de_pago_de_tarjeta_con(String arg1) throws Exception {
        Selenide.sleep(3000);
        // Input card information & Confirm order information
        $(By.name("creditCard")).val("9999999999");
        $(By.name("expiryDate")).val("04/2020");
    }

    @Then("^Realiza pago y envia pedido$")
    public void realiza_pago_y_envia_pedido() throws Exception {
        $(By.name("continue")).click();
        $(By.id("confirmMessage")).shouldHave(text("Please confirm the information below and then press submit..."));

        // Submit order
        $(By.id("order")).click();
        $(By.cssSelector(".messages li")).shouldHave(text("Thank you, your order has been submitted."));
        orderId = $(By.id("orderId")).text();
    }

    @Given("^Ir a revisar su cuenta$")
    public void ir_a_revisar_su_cuenta() throws Exception {
        Selenide.sleep(3000);
        // Show profile page
        $(By.linkText("My Account")).click();
        $(By.cssSelector("#Catalog h3")).shouldHave(text("User Information"));

        // Show orders
        $(By.linkText("My Orders")).click();
        $(By.cssSelector("#Content h2")).shouldHave(text("My Orders"));
    }

    @When("^Ingresa su pedido obtenido desde su cuenta$")
    public void ingresa_su_pedido_obtenido_desde_su_cuenta() throws Exception {
        Selenide.sleep(3000);
        // Show order detail
        $(By.linkText(orderId)).click();
        assertThat($(By.id("orderId")).text()).isEqualTo(orderId);
    }

    @Then("^Valida y cierra sesion$")
    public void valida_y_cierra_sesion() throws Exception {
        Selenide.sleep(3000);
        // Sign out
        $(By.linkText("Sign Out")).click();
        $(By.id("WelcomeContent")).shouldHave(exactText(""));
    }

}
