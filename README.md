General description.
The task you need to achieve is to prepare a simple micro-service for the described use cases below.
The microservice have to be prepared with framework: Spring 5 and Spring and SpringBoot 2.X (x>=4).
In below use cases you will find the requirement to collect currency rates from NBP service - it is service
delivered by National Bank of Poland (NBP) for which the description of API is available on the
http://api.nbp.pl/en.html web site.
Please be informed that during code review we will pay attention if your implementation follows
the best practices according to design pattern and obviously if solution meets requirements in
use cases.

Use Case 1.
The service has to have possibilities to receive sell exchange rate expressed in the PLN currency for the
given currency and the date of publication of the exchange rate table by the NBP. To maximalize
performance of downloading the same data, collected rates have to be cached in local database (ex. H2
database), so the main business constraint for your task is the rule that the micro-service can return data to
the client in the response only if all data from the NBP firstly was persisted in the local database.
Use Case 2.
The service has to have possibility to calculate the total purchasing cost in PLN of given list of foreign
currencies for given date. The request will contain list of foreign currencies in format of three-letter
currency code (ISO 4217 format). The total cost in PLN has to be recalculated using middle exchange rate
of currencies.
Acceptance criteria:
1. The project has proper structure: from micro-service architecture and Spring framework point of
view.
2. The project has proper structure from the maven project point of view.
3. The source code is clean and easy to read for others.
4. The micro-service works properly according to the requirements described in UC1 and UC2.
5. The collected data from the NBP are persisted in local database.
6. The database is automatically created only if not exists during starting micro-service.
