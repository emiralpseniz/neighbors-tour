# Neighbors Tour

Calculate how many rounds of visits you can do with given budget.

#### GET /budget?startingCountry=&currency=&budgetPerCountry=&totalBudget=

##### Parameters
`startingCountry: String (required)`: ISO (2 or 3 char) code of starting country
`currency: String (required)`: ISO code of currency of budget
`budgetPerCountry: int (required)`: Budget planned to be spend for every neighbor
`totalBudget: int (required)`: Total planned budget

This API can be used to calculate how many neighbor tours can be done given budget.

##### Response
```json
{
  "numberOfTrips":2,
  "remainingBudget":200,
  "budgetInLocalCurrencies":{
    "TUR":"1514.06 TRY",
    "SRB":"200.00 EUR",
    "MKD":"200.00 EUR",
    "GRC":"200.00 EUR",
    "ROU":"967.86 RON"
  }
}
```

Two external APIs are used to obtain starting country's neighbors and exchange rates of these countries.
If given neighbors exchange is unknown, input currency will be used in calculating budget in local currency.

#### Security
API is secured with oAuth2 and uses Google as identity provider.
Unfortunately there isn't any custom login form yet, so pretty much all process is handled by Google.

#### Setup
To use the application, following parameters are needed:
- spring.security.oauth2.client.registration.google.client-id=Google oAuth2 client id
- spring.security.oauth2.client.registration.google.client-secret=Google oAuth2 client secret
- rapidapi.key=Rapid API client key

After that you're good to go!

#### What's next
- Custom login page
- Tests
- Caching







