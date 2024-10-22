

var tests = new List<Tuple<int, int>>() {
    new Tuple<int, int>(10000, 100),
    new Tuple<int, int>(100000, 1000),
    new Tuple<int, int>(200000, 2000),
    new Tuple<int, int>(500000, 5000),
};



foreach (var test in tests)
{
    Data.InitDataBase(test.Item1, test.Item2);
    Console.WriteLine($"{test.Item1},{test.Item2}");
    Queries.NavigationProperty();
    Queries.Manual();
    Queries.ManualCaching();
    Queries.AllInOneCall();
    Queries.SqlLikeJoin();
    Queries.SQL();
}
