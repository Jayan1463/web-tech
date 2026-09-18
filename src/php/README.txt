Start the PHP validation service from the project root:

php -S localhost:18082 -t src/php

The GroceryHub registration form uses this service through Ajax. The Java
RegisterServlet remains the final server-side registration and database check.
