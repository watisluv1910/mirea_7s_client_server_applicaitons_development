try {
    db = db.getSiblingDB("pract5-db")
    print("CREATING USER")
    db.createUser(
        {
            user: "dev",
            pwd: "dev",
            roles: [{ role: "readWrite", db: "pract5-db" }]
        }
    );
    db.createCollection("users")
} catch (error) {
    print(`Failed to create developer db user:\n${error}`);
}
