workspace "A simple workspace" {
	model {
		user = person "user"
		system = softwareSystem "The system" {
			url "https://github.com/Riduidel/C4PlantUML2Structurizr"

			cont = container "The container" {
				description "It is described"

				comp = component "The component"
			}
		}
		user -> system "yes it uses"
	}
	views {
	}
}