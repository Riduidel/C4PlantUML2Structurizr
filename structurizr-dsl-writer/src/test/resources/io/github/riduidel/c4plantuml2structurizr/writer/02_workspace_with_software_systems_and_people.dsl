workspace "A simple workspace" {
	model {
		user = person "user"
		system = softwareSystem "system"
		user -> system "yes it uses"
	}
	views {
	}
}
