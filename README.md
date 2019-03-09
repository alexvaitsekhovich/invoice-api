
Invoicing API
-


Simple API to fetch invoices and invoice positions.

#### Dependencies

- Invoices database (MySQL)


#### How to run it:


In the directory `dev` run:

```shell
    sh start_api.sh 
 ```

#### Endpoints:

Now these endpoints are available:

List all invoices by the debtor id:
`http://localhost:8092/invoicesall/<debtor_id>`

[here](http://localhost:8092/invoicesall/12)

List all invoices by the debtor id with pagination:
`http://localhost:8092/invoices/<debtor_id>?page=<page>`

[here](http://localhost:8092/invoices/12?page=0)

Show all details of the invoice with the list of all positions:
`http://localhost:8092/invoicedetailed/<invoice_id>`

[here](http://localhost:8092/invoicedetailed/1)

#### How to use own data:


In the example database only data for the debtor #12 and positions for the invoice #1 are set.
Data can be written to the database either before the start in the `dev/invoices_init.sql` or after start in the database:
```
host: 127.0.0.13308
user: root
password: rootpass 
```

