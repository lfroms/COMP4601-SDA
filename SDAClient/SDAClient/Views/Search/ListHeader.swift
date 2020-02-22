//
//  ListHeader.swift
//  SDAClient
//
//  Created by Lukas Romsicki on 2020-02-21.
//  Copyright © 2020 Lukas Romsicki. All rights reserved.
//

import SwiftUI

struct ListHeader: View {
    var body: some View {
        VStack {
            Spacer()
            HStack {
                SearchBar()
            }
            Spacer()
        }
        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
        .background(Color.secondary.opacity(0.1))
        .background(Color.primary.colorInvert())
    }
}

struct ListHeader_Previews: PreviewProvider {
    static var previews: some View {
        ListHeader()
    }
}
