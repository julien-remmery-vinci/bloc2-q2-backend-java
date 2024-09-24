| URI                     | Méthode HTTP | Auths? | Opération                                                                                                           |
|-------------------------|--------------|--------|---------------------------------------------------------------------------------------------------------------------|
| **pages**               | GET          | JWT    | READ ALL : Lire toutes les ressources de la collection                                                              |
| **pages/{id}**          | GET          | JWT    | READ ONE : Lire la ressource identifiée                                                                             |
| **pages**               | POST         | JWT    | CREATE ONE : Créer une ressource basée sur les données de la requête                                                |
| **pages/{id}**          | DELETE       | JWT    | DELETE ONE : Effacer la ressource identifiée                                                                        |
| **pages/{id}**          | PUT          | JWT    | UPDATE ONE : Replacer l'entièreté de la ressource par les données de la requête                                     |
| **news/{id}**           | GET          | Non    | READ ONE : Lire la ressource identifiée si elle est published                                                       |
| **news/{id}**           | GET          | JWT    | READ ONE : Lire la ressource identifiée si elle appartient à l'utilisateur authentifié                              |
| **news**                | GET          | Non    | READ ALL : Lire toutes les ressources dont le status vaut published                                                 |
| **news**                | GET          | JWT    | READ ALL : Lire toutes les ressources appartenantes à l'utilisateur authentifié & published                         |
| **news/page/{id}**      | GET          | Non    | READ ALL : Lire toutes les ressources pour la page identifiée dont le status vaut published                         |
| **news/page/{id}**      | GET          | JWT    | READ ALL : Lire toutes les ressources appartenantes à l'utilisateur authentifié & published pour la page identifiée |
| **news**                | POST         | JWT    | CREATE ONE : Créer une ressource basée sur les données de la requête                                                |
| **news/{id}**           | DELETE       | JWT    | DELETE ONE : Effacer la ressource identifiée                                                                        |
| **news/{id}**           | PUT          | JWT    | UPDATE ONE : Replacer l'entièreté de la ressource par les données de la requête                                     |
| **news/{id}/associate** | POST         | JWT    | ASSOCIATE ONE : Associer une news à la page identifiée                                                              |
