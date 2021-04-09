const itemList = document.getElementById('albumsList')
const searchBar = document.getElementById('searchInput')

const allItems = [];

fetch("http://localhost:8000/items/api").
then(response => response.json()).
then(data => {
    for (let item of data) {
        allItems.push(item);
    }
})

searchBar.addEventListener('keyup', (e) => {
    const searchingCharacters = searchBar.value.toLowerCase();
    let filteredItems = allItems.filter(item => {
        return item.name.toLowerCase().includes(searchingCharacters);
    });
    displayItems(filteredItems);
})


const displayItems = (items) => {
    itemList.innerHTML = items
        .map((i) => {
            return `<div class="offer card col-sm-6 col-md-3  col-lg-2 m-1 p-0">
                <a th:href="@{/items/details/(id=*{id})}">
                    <div class="card-img-top-wrapper">
                        <img src="${i.imgUrl}" class="card-img-top" alt="Thumbnail [100%x225]"
                             data-holder-rendered="true"
                             style="height: 225px; width: 100%; display: block;">
                    </div>
                    <div class="card-body pb-1">
                    <h5 class="card-title text-center" >Brand: Nike</h5>
                    </div>
                    <div class="card-body pb-1">
                        <h5 class="card-title text-center" >Model: ${i.name}</h5>
                    </div>
                    <div class="card-body pb-1">
                        <h5 class="card-title text-center" >Gender: ${i.gender}</h5>
                    </div>
                    <ul class="offer-details list-group list-group-flush">
                        <li class="list-group-item">
                            <div class="card-text text-center"><span>Price: ${i.price}</span></div>
                        </li>
                    </ul>
                    <a href="/items/details/${i.id}" type="button" class="btn btn-info" >Details</a>
                </a>
            </div>`
        })
        .join('');

}