function populateUserExpenditure()
{
    let url = "/app/gateway/expense/all";
    let expenditure = loadJson(url) // send query to get user expenditure

    // Giving it dummy values for now
    // expenditure =
    //     [
    //         {
    //             "title": "Grocery Shopping",
    //             "cost": "50",
    //             "currency": "USD",
    //             "date": "2023-09-27",
    //             "category": "Food"
    //         },
    //
    //         {
    //             "title": "Movie",
    //             "cost": "30",
    //             "currency": "USD",
    //             "date": "2023-09-28",
    //             "category": "Entertainment"
    //         },
    //
    //         {
    //             "title": "PS5",
    //             "cost": "500",
    //             "currency": "USD",
    //             "date": "2023-09-29",
    //             "category": "Entertainment"
    //         }
    //     ]

    let horizontal_bar_open = "<div class=\"horizontal-bar\">";
    let firstColOpenAndClose = "<div class=\"col-5\"></div>";
    let secondColOpen = "<div class=\"col-4\">";
    let closeDiv = "</div>";

    let titleEntryDiv = "<div class=\"bar-title\">Title: ";
    let costEntryDiv = "<div class=\"bar-cost\">Cost: ";
    let dateEntryDiv = "<div class=\"bar-date\">Date: ";
    let categoryEntryDiv = "<div class=\"bar-category\">Category: ";

    let expenditureContent = "";

    let currencySym =
        {
            "USD": "$"
        };

    let categoryCounter = {};
    let pieChartData = [];
    console.log(expenditure);
    let userID = Number(getCookie("userID"));

    for (let i=0; i<expenditure.length; i++)
    {
        if(expenditure[i]["owedUserId"] === userID && expenditure[i]["groupId"] === 0 && expenditure[i]["owingUserId"][0] === 0 ) {
            let title = expenditure[i]["title"];
            let cost = expenditure[i]["amount"];
            let currency = currencySym[expenditure[i]["currency"]];
            let date = expenditure[i]["date"];
            let category = expenditure[i]["category"];
            let note = expenditure[i]["note"];
            let newRow =
                horizontal_bar_open +
                firstColOpenAndClose +
                secondColOpen +
                titleEntryDiv + title + closeDiv +
                costEntryDiv + currency + cost + closeDiv +
                dateEntryDiv + date + closeDiv +
                categoryEntryDiv + category + closeDiv +
                closeDiv +
                closeDiv;

            expenditureContent += newRow;
            if (!Object.hasOwn(categoryCounter, category))
            {
                categoryCounter[category] = 0;
            }
            categoryCounter[category] += 1;
        }
    }

    for (const [key, value] of Object.entries(categoryCounter)) {
        pieChartData.push({
            x: key,
            value: value
        });
    }

    document.getElementById("expenditureContent").innerHTML = expenditureContent;
    generatePIchart(pieChartData);
}

function generatePIchart(data){
    // data = [
    //     {x: "White", value: 223553265},
    //     {x: "Black or African American", value: 38929319},
    //     {x: "American Indian and Alaska Native", value: 2932248},
    //     {x: "Asian", value: 14674252},
    //     {x: "Native Hawaiian and Other Pacific Islander", value: 540013},
    //     {x: "Some Other Race", value: 19107368},
    //     {x: "Two or More Races", value: 9009073}
    // ];

    // create the chart
    let chart = anychart.pie();

    // set the chart title
    chart.title("Expenditure Category");

    // add the data
    chart.data(data);

    // display the chart in the container
    chart.container('pieChart');
    chart.draw();

}

function submitPersonalExpense()
{
    let title = document.getElementById("title").value;
    let amount = document.getElementById("amount").value;
    let notes = document.getElementById("notes").value;
    let currency = document.getElementById("currency").value;
    let paymentMethod = document.getElementById("paymentMethod").value;
    let category = document.getElementById("category").value;
//    let owedUserId = document.getElementBy("owedUser").value;

    let payloadJson = {
            title: title,
            note: notes,
            category: category,
            amount: parseFloat(amount),
            currency: currency,
    //        owedUserId: "",
    //        owingUserIds: [],
    //        groupId: ""
        }

        submitExpense(payloadJson);
}