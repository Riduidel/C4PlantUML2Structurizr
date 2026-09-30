workspace "A simple workspace" {
	model {
		properties {
			"structurizr.groupSeparator" "/"
		}
		group "Outer" {
			group "Inner" {
				user = person "user"
			}
			system = softwareSystem "system"
		}
		user -> system "yes it uses"
	}
	views {
	}
}