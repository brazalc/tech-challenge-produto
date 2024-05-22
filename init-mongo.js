use admin
db.createUser(
   {
     user: "myuser",
     pwd: "secret",
     roles: [ { role: "readWrite", db: "produtos" }, { role: "dbAdmin", db: "produtos" } ],
     mechanisms:[  
       "SCRAM-SHA-1"
     ]
   }
)