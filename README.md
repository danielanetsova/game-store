## Name
Game Store

### Description
The game store is a platform, where users can register(the first registered user becomes an administrator), log in, view games and buy games.  

### Functionality
- All users can view all games.
- All users can view the details of each game.
- Logged-in users can logout.
- Logged in users can add/remove games from their shopping cart.
- Logged in users can buy games that are added to the shopping cart and those games are added to the
profile of the user and cannot be bought for a second time.
- Administrators can add, edit or delete games.
- Basic user can not add, edit or delete game.

### Commands
- RegisterUser|email|password|confirmPassword|fullName - add a new
user to the database in case of valid parameters.
- LoginUser|email|password - set the current logged in user if it exists. Otherwise,
print an appropriate message.
- Logout - log out the user from the system. If there is no logged in user, print an appropriate
message.
- AddGame|title|price|size|trailer|thubnailURL|description|releaseDate - add game if matches the given criteria.
- EditGame|id|values - a game should be edited in case of valid id and if matches the given criteria.
- DeleteGame|id - a game should be deleted in case of valid id. Otherwise, print an appropriate message.
- AllGames - print titles and price of all games.
- DetailsGame|gameTitle - print details for а single game.
- OwnedGames – print the games bought by the currently logged in user.
- AddItem|gameTitle - add game to shopping cart
- RemoveItem|gameTitle - remove game from shopping cart
- BuyItem – buy all games from shopping cart. If the user owns a game, he shouldn't be able to add it to the shopping cart.
