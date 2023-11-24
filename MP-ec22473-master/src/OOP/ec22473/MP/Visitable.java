package OOP.ec22473.MP;

interface Visitable {
    
    Direction visit( // Returns direction the visitor leaves towards.
        Visitor visitor,
        Direction directionVistorArrivesFrom);
}