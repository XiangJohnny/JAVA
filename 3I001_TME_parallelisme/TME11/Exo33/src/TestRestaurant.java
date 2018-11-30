public class TestRestaurant {

	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant(2);
		GroupeClients gp1 = new GroupeClients(1, restaurant);
		GroupeClients gp2 = new GroupeClients(2, restaurant);
	}

}
