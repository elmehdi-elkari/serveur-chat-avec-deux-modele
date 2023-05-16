# serveur-chat-avec-deux-modele


Ce projet est un serveur de chat qui permet à plusieurs utilisateurs de communiquer entre eux en temps réel. Il implémente deux modèles différents pour gérer les connexions et les E/S : un modèle single thread I/O blockant et un modèle multi thread I/O blockant.

## Single Thread I/O Blockant

Le modèle single thread I/O blockant utilise un seul thread pour gérer toutes les connexions et les E/S. Lorsqu'un client se connecte, 
le thread principal accepte la connexion et traite les messages entrants et sortants de manière séquentielle. 
Cela signifie que les opérations d'E/S bloquent le thread principal jusqu'à leur achèvement, ce qui peut ralentir le traitement des autres connexions.



## Multi Thread I/O Blockant

Le modèle multi thread I/O blockant utilise plusieurs threads pour gérer les connexions et les E/S. Chaque fois qu'un client se connecte, 
un nouveau thread est créé pour traiter les messages entrants et sortants de ce client spécifique. 
Cela permet de gérer plusieurs connexions simultanément et de réduire l'impact des opérations d'E/S bloquantes sur les autres clients.


## Configuration

Assurez-vous de configurer correctement le serveur en modifiant les paramètres appropriés, tels que l'adresse IP et le port d'écoute.
